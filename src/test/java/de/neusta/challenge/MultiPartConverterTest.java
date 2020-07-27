package de.neusta.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

public class MultiPartConverterTest {

  private MultiPartConverter multiPartConverter;

  @Mock
  private MultipartFile multiFileMock;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    this.multiPartConverter = new MultiPartConverter();
  }

  /**
   * @author limmoor
   * @throws IllegalStateException
   * @since 10.08.2018
   */
  @Test
  public void testConvert() {
    BDDMockito.given(this.multiFileMock.getOriginalFilename()).willReturn("name.csv");

    // @WHEN
    final File file = this.multiPartConverter.convert(this.multiFileMock);
    // @THEN
    assertNotNull(file);
    assertEquals(this.multiFileMock.getOriginalFilename(), file.getName());
  }

  /**
   * @author limmoor
   * @throws IOException
   * @throws IllegalStateException
   * @since 17.08.2018
   */
  @Test
  public void testConvertCaseInvalidFile() throws IllegalStateException, IOException {

    // @GIVEN
    BDDMockito.willThrow(IOException.class).given(this.multiFileMock)
        .transferTo(ArgumentMatchers.any());

    // @THEN
    final ValidationException exception = assertThrows(ValidationException.class,
        () -> this.multiPartConverter.convert(this.multiFileMock));

    assertEquals(ErrorMessage.CONVERT_ERROR.asJSON(), exception.getMessage());
  }

}
