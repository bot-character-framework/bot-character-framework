/**
 * MIT License
 *
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package org.bcf.examplebot.skills

import org.bcf.domain.ConversationExpectation
import org.bcf.domain.ConversationSession
import org.bcf.domain.StructuredMessage
import org.bcf.examplebot.MyBot
import org.bcf.impl.BaseSkill
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

/**
 * @author Dmitry Berezovsky (corvis)
 */
private data class Forecast(
        var temperature: Float,
        var weather: String,
        var clouds: Boolean, var rain: Boolean,
        var location: String? = null,
        var time: String? = null) {

    fun getFullForecast(): String {
        val cloudState = if (clouds) "cloudy" else "sunny"
        val rainState = if (rain) "with showers" else ""
        return "$temperature degrees celsius, $cloudState $rainState $weather"
    }
}

class WeatherInfo : BaseSkill<MyBot, MyBot.PhraseBook>() {
    private val forecastVariations = listOf<Forecast>(
            Forecast(22.5f, "", true, true),
            Forecast(17.0f, "", false, false),
            Forecast(12f, "", true, false),
            Forecast(18.4f, "", true, false)
    )

    private object CONTEXT_KEY {
        val LOCATION = "location"
    }

    private object ENTITY {
        val ADDRESS = "address"
    }

    private object INTENT {
        val WEATHER_GENERAL = "weather" // What is the weather doday?
        val ACTIVITY = "weather.activity"
        val CONDITION = "weather.condition" // Is it sunny now?
        val OUTFIT = "weather.outfit"   // Should I take an umbrella this evening?
        val TEMPERATURE = "weather.temperature" // What is the temperature?
    }

    private object EXPECTATION {
        val ADDRESS = ConversationExpectation(WeatherInfo::class.java).setName("address").addEntity(ENTITY.ADDRESS)!!
    }

    override fun handleMessage(@NotNull message: StructuredMessage, @NotNull session: ConversationSession<MyBot.PhraseBook>) {
        if (message.intent.id == null) {
            throw IllegalStateException("Intent shouldn't be null")
        }
        // If we do not know location - ask user
        when (message.intent.id) {
            INTENT.WEATHER_GENERAL -> {
                val forecast = getForecastForUserMessage(message, session) ?: return
                session.reply(MyBot.PhraseBook.FORECAST, mapOf(
                        "forecast" to forecast.getFullForecast(),
                        "location" to forecast.location)
                )
            }
            INTENT.TEMPERATURE -> {
                val forecast = getForecastForUserMessage(message, session) ?: return
                session.reply(MyBot.PhraseBook.TEMPERATURE, mapOf(
                        "temperature" to forecast.temperature.toInt().toString())
                )
            }
        }
    }

    override fun handleExpectation(expectation: ConversationExpectation, message: StructuredMessage, session: ConversationSession<MyBot.PhraseBook>): StructuredMessage? {
        when (expectation.name) {
            EXPECTATION.ADDRESS.name -> {
                // TODO: need to remember address (put into context) and invoke original message handler
                val addressEntity = message.entities.find { it.typeId == ENTITY.ADDRESS }
                // If address still is not there - ask user one more time
                if (addressEntity == null) {
                    session.reply(MyBot.PhraseBook.DID_NOT_GET_IT)
                    session.expect(expectation)
                    return null
                }
                session.context.put(CONTEXT_KEY.LOCATION, addressEntity.value)
                return expectation.source // Process original message then
            }
        }
        return null
    }

    private fun getLocationOrAskUser(message: StructuredMessage, session: ConversationSession<MyBot.PhraseBook>): String? {
        val location = message.entities.find { it.typeId == ENTITY.ADDRESS }
        if (location == null) {
            // If request doesn't contain location - check the context
            if (session.context.has(CONTEXT_KEY.LOCATION)) {
                return session.context.getString(CONTEXT_KEY.LOCATION)
            }
            session.reply(MyBot.PhraseBook.ASK_FOR_LOCATION)
            session.expect(EXPECTATION.ADDRESS.newFromTemplate(message).setTargetSkill(this))
            return null
        }
        return location.value
    }

    override fun canHandle(intentId: String): Boolean {
        return intentId.startsWith("weather")
    }


    private fun getForecastForUserMessage(message: StructuredMessage, session: ConversationSession<MyBot.PhraseBook>): Forecast? {
        val location = getLocationOrAskUser(message, session) ?: return null
        return loadForecast(location)
    }

    private fun loadForecast(location: String, targetDate: LocalDate = LocalDate.now()): Forecast {
        val forecast = forecastVariations[(Math.random() * forecastVariations.size).toInt()]
        return forecast.copy(location = location, time = targetDate.toString())
    }
}
