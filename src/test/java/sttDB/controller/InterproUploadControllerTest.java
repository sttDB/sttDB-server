package sttDB.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sttDB.service.FileManager;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class InterproUploadControllerTest {

    // TODO: test this
//    InterproUploadController sut;
//
//    @Mock
//    FileManager fileManager;
//
//    MultipartHttpServletRequest request;
//
//    @Mock
//    MultipartFile mockFile;
//
//    @Before
//    public void setUp() {
//        sut = new InterproUploadController(fileManager);
//    }
//
//    @Test
//    public void ifExperimentExistsThenFileIsSaved() throws Throwable {
//        request = mock(MultipartHttpServletRequest.class);
//        when(request.getParameter("experiment")).then(invocationOnMock -> "sttDB-exp-1");
//
//        sut.processRequest(request);
//
//        verify(fileManager, times(1)).saveFile(mockFile);
//    }

}