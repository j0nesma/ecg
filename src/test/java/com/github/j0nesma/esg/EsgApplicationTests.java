package com.github.j0nesma.esg;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.j0nesma.esg.EsgApplication;
import com.github.j0nesma.esg.utils.CustomerPopulator;

@SpringBootTest(properties = {"csvfile=test-data.csv"})
public class EsgApplicationTests {

    @MockBean
    private CustomerPopulator customerPopulator;

	@Autowired
    private EsgApplication esgApplication;

    @Test
    void testRunApplication() throws Exception {
        // Arrange
		
        // Act
        esgApplication.run();

        // Assert
        verify(customerPopulator, times(2)).populate(any(Path.class));
        verifyNoMoreInteractions(customerPopulator);
    }
}
