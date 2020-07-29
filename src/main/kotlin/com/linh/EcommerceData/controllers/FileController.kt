package com.linh.EcommerceData.controllers

import com.linh.EcommerceData.services.DataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("")
class FileController(
        @Autowired
        private val dataService : DataService
) {


    @GetMapping("")
    fun index(model: Model): String {
        model.addAttribute("records", dataService.getRecords())
        return "index"
    }

    @PostMapping("/file")
    fun uploadFile(@RequestParam("file") file : MultipartFile, redirectAttributes : RedirectAttributes ): String {
        dataService.processFile(file)
        return "redirect:/"
    }

}