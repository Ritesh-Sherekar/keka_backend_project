package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.shiftDto.ShiftRequestDto;
import com.example.KekaActionService.entity.Shift;
import com.example.KekaActionService.repository.ShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftPostService {
    @Autowired
    private ShiftRepo shiftRepo;

    // Add Shift
    public Shift addShift(ShiftRequestDto shiftDto){
        Shift shift = new Shift();
        shift.setShiftName(shiftDto.getShiftName());
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        return shiftRepo.save(shift);
    }

    // Add List OF Shift
    public List<Shift> addListOfShift(List<Shift> shiftList){
        return shiftRepo.saveAll(shiftList);
    }
}
