/**
 * MIT License
 * <p>
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package org.bcf.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data object representing character model
 *
 * @author Dmitry Berezovsky (corvis)
 */
public class CharacterModel {
    public class CharacterOptions {
        private float formal = 0;
        private float emotional = 0;

        public float getFormal() {
            return formal;
        }

        public CharacterOptions setFormal(float formal) {
            this.formal = formal;
            return this;
        }

        public float getEmotional() {
            return emotional;
        }

        public CharacterOptions setEmotional(float emotional) {
            this.emotional = emotional;
            return this;
        }
    }

    public static class Line {
        private String text;
        private float formal = 0;
        private float emotional = 0;

        public String getText() {
            return text;
        }

        public Line setText(String text) {
            this.text = text;
            return this;
        }

        public float getFormal() {
            return formal;
        }

        public Line setFormal(float formal) {
            this.formal = formal;
            return this;
        }

        public float getEmotional() {
            return emotional;
        }

        public Line setEmotional(float emotional) {
            this.emotional = emotional;
            return this;
        }
    }

    public static class LineVariations {
        private String id;
        private String category;
        private List<Line> variations = new ArrayList<>();

        public String getId() {
            return id;
        }

        public LineVariations setId(String id) {
            this.id = id;
            return this;
        }

        public String getCategory() {
            return category;
        }

        public LineVariations setCategory(String category) {
            this.category = category;
            return this;
        }

        public List<Line> getVariations() {
            return variations;
        }

        public LineVariations setVariations(List<Line> variations) {
            this.variations = variations;
            return this;
        }
    }

    private String id;
    private String name;
    private String lang;
    private String version;
    @JsonIgnore
    private Class<? extends Enum> phrasebook;
    @JsonProperty("character")
    private CharacterOptions characterOptions;
    private Map<String, LineVariations> model = new HashMap<>();

    public CharacterModel() {
    }

    protected CharacterModel(Class<? extends Enum> phrasebook) {
        this.phrasebook = phrasebook;
    }

    public String getId() {
        return id;
    }

    public CharacterModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CharacterModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public CharacterModel setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public CharacterModel setVersion(String version) {
        this.version = version;
        return this;
    }

    public Map<String, LineVariations> getModel() {
        return model;
    }

    public CharacterModel setModel(Map<String, LineVariations> model) {
        this.model = model;
        return this;
    }

    @JsonIgnore
    public Class<? extends Enum> getPhrasebook() {
        return phrasebook;
    }

    @JsonIgnore
    protected CharacterModel setPhrasebook(Class<? extends Enum> phrasebook) {
        this.phrasebook = phrasebook;
        return this;
    }
}
