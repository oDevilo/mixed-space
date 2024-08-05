/*
 * Copyright 2020-2024 OpenFlow Team (https://github.com/openflow-io).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.devil.mixed.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.devil.mixed.utils.time.LocalDateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Brozen
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    /**
     * 序列化时，写出到JSON字符串中的日期的格式
     */
    private final String pattern;

    public LocalDateTimeSerializer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void serialize(LocalDateTime localDateTime,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(LocalDateTimeUtils.format(localDateTime, pattern));
    }


    @Override
    public Class<LocalDateTime> handledType() {
        return LocalDateTime.class;
    }
}