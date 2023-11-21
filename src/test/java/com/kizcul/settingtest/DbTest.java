package com.kizcul.settingtest;

import com.kizcul.dto.TesttbDto;
import com.kizcul.entity.test.Testtb;
import com.kizcul.entity.test.TesttbRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DbTest {

    private final TesttbRepository testtbRepository;

    @Autowired
    public DbTest(TesttbRepository testtbRepository){
        this.testtbRepository = testtbRepository;
    }

    @Test
    public void connect(){
        List<TesttbDto> dtos = new ArrayList<>();
        List<Testtb> entities = testtbRepository.findAll();
        for (Testtb entity : entities) {
            TesttbDto dto = TesttbDto.builder()
                    .col1(entity.getCol1())
                    .col2(entity.getCol2())
                    .col3(entity.getCol3().toString())
                    .build();
            dtos.add(dto);
        }
        for (TesttbDto dto : dtos) {
            System.out.println("dto = " + dto);
        }
    }

}
