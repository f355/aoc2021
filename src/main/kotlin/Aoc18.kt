import kotlin.math.max

class Aoc18(day: String) : Aoc(day) {
    override fun run() {
        println(solve(readFile(true)))
        println(solve(readFile(false)))
    }


    private fun solve(input: List<String>): Pair<Long, Long> {
        val nums = input.map { r ->
            val (p, _) = parse(r, null)
            p
        }
        val res1 = nums.map { it.clone() }.reduce(::add)
        var res2 = Long.MIN_VALUE
        for (i in nums.indices) {
            for (j in nums.indices) {
                if (i == j) continue
                res2 = max(res2, magn(add(nums[i].clone(), nums[j].clone())))
            }
        }
        return Pair(magn(res1), res2)
    }

    private fun magn(p: P): Long {
        if (!p.isPair) {
            return p.v!!
        }
        return 3 * magn(p.l!!) + 2 * magn(p.r!!)
    }

    private fun add(l: P, r: P): P {
        val p = P()
        l.p = p
        r.p = p
        p.l = l
        p.r = r
        while (true) {
            if (explode(p)) continue
            if (split(p)) continue
            break
        }
        return p
    }

    private fun explode(p: P): Boolean {
        val toExp = findExplosive(p, 0) ?: return false
        expLeft(toExp, toExp.p, toExp.l!!.v!!)
        expRight(toExp, toExp.p, toExp.r!!.v!!)
        toExp.l = null
        toExp.r = null
        toExp.v = 0
        return true
    }

    private fun split(p: P): Boolean {
        if (!p.isPair) {
            if (p.v!! < 10) return false
            val half = p.v!! / 2
            p.l = P(p, null, null, half)
            p.r = P(p, null, null, p.v!! - half)
            p.v = null
            return true
        }
        return split(p.l!!) || split(p.r!!)
    }

    private fun expLeft(me: P, p: P?, v: Long): Boolean {
        p ?: return false
        return if (p.r === me)
            setRight(p.l!!, v)
        else
            expLeft(p, p.p, v)
    }

    private fun setRight(p: P, v: Long): Boolean =
        if (!p.isPair) {
            p.v = p.v!! + v
            true
        } else {
            setRight(p.r!!, v)
        }

    private fun expRight(me: P, p: P?, v: Long): Boolean {
        p ?: return false
        return if (p.l === me)
            setLeft(p.r!!, v)
        else
            expRight(p, p.p, v)
    }

    private fun setLeft(p: P, v: Long): Boolean =
        if (!p.isPair) {
            p.v = p.v!! + v
            true
        } else {
            setLeft(p.l!!, v)
        }

    private fun findExplosive(p: P, depth: Int): P? = when {
        !p.isPair -> null
        depth == 4 -> p
        else -> findExplosive(p.l!!, depth + 1)
            ?: findExplosive(p.r!!, depth + 1)
    }

    private fun parse(inp: String, parent: P?): Pair<P, String> {
        val reDigit = "^([0-9]+)(.*)".toRegex()
        val m = reDigit.find(inp)
        val p = P(parent)
        return when {
            inp[0] == '[' -> {
                val (left, rest) = parse(inp.removePrefix("["), p)
                val (right, rest1) = parse(rest.removePrefix(","), p)
                p.l = left
                p.r = right
                Pair(p, rest1.removePrefix("]"))
            }
            m != null -> {
                p.v = m.groupValues[1].toLong()
                Pair(p, m.groupValues[2])
            }
            else -> {
                error("can't parse $inp!")
            }
        }
    }

    data class P(
        var p: P? = null,
        var l: P? = null,
        var r: P? = null,
        var v: Long? = null
    ) {
        val isPair
            get() = v == null

        fun render(): String {
            if (!isPair) {
                return v.toString()
            }
            return "[${l!!.render()},${r!!.render()}]"
        }

        fun clone(): P {
            val c = P(p, v = this.v)
            c.l = l?.clone()?.also { it.p = c }
            c.r = r?.clone()?.also { it.p = c }
            return c
        }
    }
}
