package com.market.shopservice.util;

import com.market.shopservice.dto.ProductDto;
import com.market.shopservice.dto.SalaryDto;
import com.market.shopservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DayScheduler {

    private final ProductService productService;

    public void schedule(SalaryDto salaryDto) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Calendar now = Calendar.getInstance();
            int currentDay = now.get(Calendar.DAY_OF_MONTH);
            int targetDay = currentDay - salaryDto.getSalaryDays();
            int targetMonth = now.get(Calendar.MONTH); // get the current month
            if (targetDay <= 0) {
                // if the target day is in the previous month, adjust the month and day accordingly
                targetMonth--;
                if (targetMonth < 0) {
                    targetMonth = 11; // wrap around to December
                }
                int daysInPrevMonth = now.getActualMaximum(Calendar.DAY_OF_MONTH);
                targetDay = daysInPrevMonth + targetDay;
            }
            if (now.get(Calendar.DAY_OF_MONTH) == targetDay) {
                changeProductPrice(salaryDto);
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    private void changeProductPrice(SalaryDto salaryDto) {
        List<ProductDto> byIds = productService.findByIds(salaryDto.getSalaryProductsId());
        List<ProductDto> dtoList = byIds.stream()
                .peek(product -> {
                    product.setPrice(product.getPrice() + product.getSalaryAmount());
                }).collect(Collectors.toList());
        productService.updateAll(dtoList);
    }

}
