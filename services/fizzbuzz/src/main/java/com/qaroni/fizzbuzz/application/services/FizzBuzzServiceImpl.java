package com.qaroni.fizzbuzz.application.services;

import com.qaroni.fizzbuzz.infrastructure.ports.inbound.FizzBuzzService;
import org.springframework.stereotype.Service;

@Service
public class FizzBuzzServiceImpl implements FizzBuzzService {

        @Override
        public String fizzBuzz(int number) {
            if (number % 15 == 0) {
                return "FizzBuzz";
            } else if (number % 3 == 0) {
                return "Fizz";
            } else if (number % 5 == 0) {
                return "Buzz";
            } else {
                return String.valueOf(number);
            }
        }
}
