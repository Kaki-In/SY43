package e2su.utbm.sy43project.api.serializers

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

object PairAsListSerializer : KSerializer<Pair<String, Int>> {
    @OptIn(InternalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("StringIntPair", StructureKind.LIST)

    override fun deserialize(decoder: Decoder): Pair<String, Int> {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("This class can be loaded only by JSON")
        val jsonArray = input.decodeJsonElement().jsonArray
        if (jsonArray.size != 2) throw SerializationException("Expected array of size 2")

        val first = jsonArray[0].jsonPrimitive.content
        val second = jsonArray[1].jsonPrimitive.int
        return Pair<String, Int>(first, second)
    }

    override fun serialize(encoder: Encoder, value: Pair<String, Int>) {
        val output = encoder as? JsonEncoder
            ?: throw SerializationException("This class can be saved only by JSON")
        val jsonArray = JsonArray(listOf(JsonPrimitive(value.first), JsonPrimitive(value.second)))
        output.encodeJsonElement(jsonArray)
    }
}
