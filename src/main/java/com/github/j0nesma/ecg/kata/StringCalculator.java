package com.github.j0nesma.ecg.kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringCalculator {
    Logger logger = LoggerFactory.getLogger(StringCalculator.class);
    public static final String DEFAULT_DELIM = ",";

    public Integer add(String numbers) {
        if(Objects.isNull(numbers) || numbers.isBlank()) {
            return 0;
        }
        if(numbers.startsWith("//[")) {
            numbers = cleanStringToUseDefaultDelimiter(numbers);
        }
        //New Lines to ","
        numbers = numbers.replace("\n", DEFAULT_DELIM);
        getNegatives(numbers);
        return sum(numbers);
    }

    private int sum(String numbers) {
        return Arrays.stream(numbers.split(DEFAULT_DELIM))
                     .mapToInt(Integer::parseInt)
                     .filter(x -> x >= 0)
                     .filter(x -> x <= 1000)
                     .sum();
    }

    private void getNegatives(String numbers) {
        List<Integer> ints = Arrays.stream(numbers.split(DEFAULT_DELIM))
                     .mapToInt(Integer::parseInt)
                     .filter(x -> x < 0)
                     .boxed()
                     .collect(Collectors.toList());
        logger.info("negative numbers = {}", ints.toString());
    }

    private String cleanStringToUseDefaultDelimiter(String numbers) {
        List<String> delimiters = getDelimiters(numbers);
        numbers = numbers.substring(numbers.indexOf("\n")+1);
        for(String del : delimiters) {
            numbers=numbers.replace(del, DEFAULT_DELIM);
        }
        return numbers;
    }

    private List<String> getDelimiters(String numbers) {
        List<String> delim = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[([^\\]]*)\\]");
        Matcher matcher = pattern.matcher(numbers);
        while(matcher.find()) {
                delim.add(matcher.group(1));
        }
        return delim;
    }
}
