package net.spaceflightnews.common

/**
 * mapper interface that is used to implement model mappers that maps from one layer model to another
 * FROM is the model need to be mapped
 * TO the model needed.
 *
 */
interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}
