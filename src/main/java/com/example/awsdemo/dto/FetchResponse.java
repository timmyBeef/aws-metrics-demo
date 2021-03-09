package com.example.awsdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.ArrayLen;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchResponse {
    String name;
    boolean value;
}
