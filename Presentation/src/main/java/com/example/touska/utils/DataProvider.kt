package com.example.touska.utils

import com.example.domain.models.Sort
import com.example.touska.R

object DataProvider {
    fun sortList():List<Sort> {
        val list = listOf<Sort>(
            Sort(R.string.date_submited,"submitted",true),
            Sort(R.string.worker_name,"worker_name",false),
            Sort(R.string.bloc_name,"block_name",false),
            Sort(R.string.floor_name,"floor_name",false),
            Sort(R.string.unit_name,"unit_name",false),
            Sort(R.string.post,"post",false),
            Sort(R.string.activity_name,"activity",false),
            Sort(R.string.supervisor_name,"supervisorName",false),
            Sort(R.string.contract_name,"contractorName",false)
        )

        return list
    }
}