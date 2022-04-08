package es.usj.androidapps.utils

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.io.Serializable


class OffsetBasedPageRequest(offset: Long, limit: Int, sort: Sort) : Pageable, Serializable {
    private val _pageSize: Int
    private val _offset: Long
    private val sort: Sort

    /**
     * Creates a new [OffsetBasedPageRequest] with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   can be null.
     */
    init {
        require(offset >= 0) { "Offset index must not be less than zero!" }
        require(limit >= 1) { "Limit must not be less than one!" }
        _pageSize = limit
        this._offset = offset
        this.sort = sort
    }

    /**
     * Creates a new [OffsetBasedPageRequest] with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the [Sort] to be specified, can be null.
     * @param properties the properties to sort by, must not be null or empty.
     */
    constructor(offset: Long, limit: Int, direction: Sort.Direction, properties: Array<String>) : this(
        offset,
        limit,
        Sort.by(direction, *properties)
    )

    /**
     * Creates a new [OffsetBasedPageRequest] with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    constructor(offset: Long, limit: Int) : this(offset, limit, Sort.unsorted()) {}

    override fun getPageNumber(): Int = Math.toIntExact(_offset / _pageSize)

    override fun getSort(): Sort {
        return sort
    }

    override operator fun next(): Pageable {
        return OffsetBasedPageRequest(_offset + _pageSize, _pageSize, getSort())
    }

    fun previous(): OffsetBasedPageRequest {
        return if (hasPrevious()) OffsetBasedPageRequest(_offset - _pageSize, _pageSize, getSort()) else this
    }

    override fun previousOrFirst(): Pageable {
        return if (hasPrevious()) previous() else first()
    }

    override fun first(): Pageable {
        return OffsetBasedPageRequest(0, _pageSize, getSort())
    }

    override fun withPage(pageNumber: Int): Pageable {
        return OffsetBasedPageRequest(pageNumber.toLong() * _pageSize, _pageSize, getSort())
    }

    override fun hasPrevious(): Boolean {
        return _offset > _pageSize
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OffsetBasedPageRequest) return false
        return EqualsBuilder()
            .append(_pageSize, other._pageSize)
            .append(_offset, other._offset)
            .append(sort, other.sort)
            .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder(17, 37)
            .append(pageSize)
            .append(offset)
            .append(sort)
            .toHashCode()
    }

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("limit", pageSize)
            .append("offset", offset)
            .append("sort", sort)
            .toString()
    }

    override fun getPageSize(): Int {
        return this._pageSize
    }

    override fun getOffset(): Long {
        return this._offset
    }

    companion object {
        private const val serialVersionUID = -25822477129613575L
    }
}