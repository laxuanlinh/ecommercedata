package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.services.DataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable

@Controller
@RequestMapping("")
class FileController(
        @Autowired
        private val dataService : DataService
) {


    @GetMapping("")
    fun index(model: Model): String {
//        val reactiveDataDrivenMode  : IReactiveDataDriverContextVariable = ReactiveDataDriverContextVariable( dataService.getRecords(), 10)
        model.addAttribute("records", dataService.getRecords())
        return "index"
    }

}