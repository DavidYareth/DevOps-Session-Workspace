package es.usj.androidapps.model.dto

import io.swagger.annotations.ApiModelProperty

class HealthResponseDTO() {

    @ApiModelProperty(position = 0)
    var status: String? = null

    constructor(status: String) : this() {
        this.status = status
    }

}