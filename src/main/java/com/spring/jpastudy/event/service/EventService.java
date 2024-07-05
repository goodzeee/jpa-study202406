package com.spring.jpastudy.event.service;

import com.spring.jpastudy.event.dto.request.EventSaveDto;
import com.spring.jpastudy.event.dto.response.EventDetailDto;
import com.spring.jpastudy.event.dto.response.EventOneDto;
import com.spring.jpastudy.event.entity.Event;
import com.spring.jpastudy.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional // 반드시 붙여야 함
public class EventService {

    private final EventRepository eventRepository;

    // 전체 조회 서비스
    public List<EventDetailDto> getEvents(int pageNo, String sort) {
        Pageable pageable = PageRequest.of(pageNo - 1 , 4);  // 1페이지에 4개씩 !
        Page<Event> eventPage = eventRepository.findEvents(pageable, sort);

        List<Event> events = eventPage.getContent();

        return events
                .stream().map(EventDetailDto::new)
                .collect(Collectors.toList());
    }

    // 이벤트 등록
    public void saveEvent(EventSaveDto dto) {
        Event savedEvent = eventRepository.save(dto.toEntity());
        log.info("saved event: {}", savedEvent);
//        return getEvents("date");
    }

    // 이벤트 단일 조회 (이벤트 디테일 페이지 단일 조회를 위한)
    public EventOneDto getEventDetail(Long id) {
        Event foundEvent = eventRepository.findById(id).orElseThrow();

        return new EventOneDto(foundEvent);
    }

    // 이벤트 삭제
    public void deleteEvent(Long id) {

        eventRepository.deleteById(id);
    }

    // 이벤트 수정
    public void modifyEvent(EventSaveDto dto, Long id) {

        // 조회 먼저 하고 setter 로 바꾸기
        Event foundEvent = eventRepository.findById(id).orElseThrow();

        foundEvent.changeEvent(dto); // dto에 생성자 만들어서 this로 가져와서 바꾸고

        eventRepository.save(foundEvent); // 저장
    }
}