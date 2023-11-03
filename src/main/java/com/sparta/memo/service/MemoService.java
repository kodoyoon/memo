package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
@Component

public class MemoService {
   private final  MemoRepository memoRepository;
   @Autowired
  public MemoService(MemoRepository memoRepository){
    this.memoRepository = memoRepository;
  }
  //public MemoService(ApplicationContext context) {
  // 1. "BEAN" 의  이름으로 가져오기
  // MemoRepository memoRepository = (MemoRepository) context.getBean("memoRepository");
  // this.memoRepository = memoRepository
  // }
  // 수동으로 하는 방식 (Autowired 말고)

  public MemoResponseDto createMemo(MemoRequestDto requestDto) {
    // RequestDto -> Entity
    Memo memo = new Memo(requestDto);

    //db 저장

    Memo saveMemo = memoRepository.save(memo);

    // Entity -> ResponseDto
    MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

    return memoResponseDto;
  }

  public List<MemoResponseDto> getMemos() {
    // DB 조회

    return memoRepository.findAll();

  }

  public Long updateMemo(Long id, MemoRequestDto requestDto) {

    // 해당 메모가 DB에 존재하는지 확인
    Memo memo = memoRepository.findById(id);
    if(memo != null) {
      // memo 내용 수정
      memoRepository.update(id, requestDto);


      return id;
    } else {
      throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
    }
  }

  public Long deleteMemo(Long id) {

    // 해당 메모가 DB에 존재하는지 확인
    Memo memo = memoRepository.findById(id);
    if(memo != null) {
      // memo 삭제
      memoRepository.delete(id);


      return id;
    } else {
      throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
    }
  }


}