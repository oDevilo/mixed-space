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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.devil.mixed.utils.time.LocalDateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Brozen
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * 反序列化时，从JSON字符串中读取到的日期格式
     */
    private final String pattern;

    public LocalDateTimeDeserializer(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        return LocalDateTimeUtils.parse(jsonParser.getValueAsString(), pattern);
    }

}