package de.neusta.challenge;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CSVUploadControllerITest {

  private MockMvc mockMvc;

  private CSVUploadController csvUploadController;

  @Mock
  private CsvParser csvParserMock;
  @Mock
  private DataValidator dataValidatorMock;
  @Mock
  private DataImport dataImportMock;
  @Mock
  private Storage storageMock;
  @Mock
  private MultiPartConverter multiPartConverterMock;
  @Mock
  private File fileMock;

  @Before
  public void setUp() {

    this.csvUploadController = new CSVUploadController(this.csvParserMock, this.dataValidatorMock,
        this.dataImportMock, this.storageMock, this.multiPartConverterMock);
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.csvUploadController).build();
  }

  /**
   * @author limmoor
   * @throws Exception
   * @since 10.08.2018
   */
  @SuppressWarnings("boxing")
  @Test
  public void testRESTimportFile() throws Exception {

    // @GIVEN

    final MockMultipartFile multipartFile = new MockMultipartFile("file", "path", "text/plain",
        "Spring Framework".getBytes());

    final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/import")
        .file(multipartFile);

    BDDMockito.given(this.multiPartConverterMock.convert(multipartFile)).willReturn(this.fileMock);
    BDDMockito.given(this.dataValidatorMock.validateCsvLine(ArgumentMatchers.any()))
        .willReturn(true);

    // @WHEN
    final MvcResult mvcResult = this.mockMvc.perform(builder).andExpect(status().isOk())
        .andReturn();

    // @THEN
    BDDAssertions.then(mvcResult.getResponse().getContentAsString())
        .isEqualTo("room list uploaded");

  }

}
