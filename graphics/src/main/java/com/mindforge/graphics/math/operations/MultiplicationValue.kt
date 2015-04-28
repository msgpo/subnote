package com.mindforge.graphics.math.operations

import com.mindforge.graphics.math.*
import com.mindforge.graphics.Constructor2

public trait MultiplicationValue : RealBinaryOperation {

    public companion object : Constructor2<MultiplicationValue, Real, Real> {
        override fun invoke(a: Real, b: Real): MultiplicationValue = object : MultiplicationValue, Calculatable() {
            override val subReals: Array<Real> = array(a, b)
        }
    }

    private val constructor: Constructor2<MultiplicationValue, Real, Real> get() = MultiplicationValue

    final override val priority: Int
        get() = +5

    override val description: String
        get() = "Multiplication"

    final override val operationSign: String
        get() = "*"

    final override val isOrderDependent: Boolean
        get() = false

    override fun approximate(): InternalReal {
        return value1.approximate() tryTimes value2.approximate()
    }

    override fun calculate(): Real {
        val s1: Real = value1.calculate()
        val s2: Real = value2.calculate()
        if (s1 is RealPrimitive && s2 is RealPrimitive) return real(s1.value * s2.value)

        // return this if no simplification is possible
        return this
    }


    // Code easter-egg: The first factor gets discriminated by this function, it always feels negative afterwards
    override fun minus(): MultiplicationValue = MultiplicationValue.invoke(-value1, value2)

    override val isZero: Boolean get() = value1.isZero || value2.isZero

    override val isPositive: Boolean get() = !(value1.isPositive xor value2.isPositive)

}