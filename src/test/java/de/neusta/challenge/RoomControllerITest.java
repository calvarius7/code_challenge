package de.neusta.challenge;

/**
 * @author limmoor
 * @since 09.08.2018
 */
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerITest {

  private RoomController roomController;

  private MockMvc mockMvc;

  private String roomNumber;

  @Mock
  private InputValidation inputValidationMock;

  @Mock
  private Storage storageMock;

  @Before
  public void setUp() {
    this.roomNumber = "1111";
    this.roomController = new RoomController(this.inputValidationMock, this.storageMock);
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
  }

  @Test
  public void testRESTWithRoomNumber() throws Exception {

    BDDMockito.given(this.storageMock.getRoomByNumber(this.roomNumber))
        .willReturn(new Room(this.roomNumber));

    //@formatter:off
    final MvcResult mvcResult = this.mockMvc.perform(get("/api/room/" + this.roomNumber)).andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    //@formatter:on

    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    final Room room = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        Room.class);

    BDDAssertions.then(room.getRoomNumber()).isEqualTo(this.roomNumber);
  }

  @Test
  public void testRESTroomList() throws Exception {
    final List<Room> roomList = new ArrayList<>();

    roomList.add(new Room(this.roomNumber));

    BDDMockito.given(this.storageMock.getRooms()).willReturn(roomList);

    final MvcResult mvcResult = this.mockMvc.perform(get("/api/room/")).andDo(print())
        .andExpect(status().isOk()).andReturn();

    final String result = mvcResult.getResponse().getContentAsString();

    BDDAssertions.then(result).contains("roomNumber").contains(this.roomNumber);
  }

}
