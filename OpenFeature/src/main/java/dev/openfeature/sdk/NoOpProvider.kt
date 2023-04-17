package dev.openfeature.sdk

class NoOpProvider : FeatureProvider {
    override var metadata: Metadata = NoOpMetadata("No-op provider")
    override suspend fun initialize(initialContext: EvaluationContext?) {
        // no-op
    }

    override suspend fun onContextSet(
        oldContext: EvaluationContext?,
        newContext: EvaluationContext
    ) {
        // no-op
    }

    override var hooks: List<Hook<*>> = listOf()
    override fun getBooleanEvaluation(
        key: String,
        defaultValue: Boolean
    ): ProviderEvaluation<Boolean> {
        return ProviderEvaluation(defaultValue, "Passed in default", Reason.DEFAULT.toString())
    }

    override fun getStringEvaluation(
        key: String,
        defaultValue: String
    ): ProviderEvaluation<String> {
        return ProviderEvaluation(defaultValue, "Passed in default", Reason.DEFAULT.toString())
    }

    override fun getIntegerEvaluation(
        key: String,
        defaultValue: Int
    ): ProviderEvaluation<Int> {
        return ProviderEvaluation(defaultValue, "Passed in default", Reason.DEFAULT.toString())
    }

    override fun getDoubleEvaluation(
        key: String,
        defaultValue: Double
    ): ProviderEvaluation<Double> {
        return ProviderEvaluation(defaultValue, "Passed in default", Reason.DEFAULT.toString())
    }

    override fun getObjectEvaluation(
        key: String,
        defaultValue: Value
    ): ProviderEvaluation<Value> {
        return ProviderEvaluation(defaultValue, "Passed in default", Reason.DEFAULT.toString())
    }

    data class NoOpMetadata(override var name: String?) : Metadata
}