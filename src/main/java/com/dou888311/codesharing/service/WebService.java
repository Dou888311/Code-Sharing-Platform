package com.dou888311.codesharing.service;

import com.dou888311.codesharing.entity.CodeBody;
import com.dou888311.codesharing.repository.CodeBodyRepository;
import com.dou888311.codesharing.support.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class WebService {

    @Autowired
    CodeBodyRepository codeRepository;

    public String getCode(String id, Model model) {
        if (codeRepository.existsById(id)) {
            if (!codeRepository.findCodeBodyById(id).isAlwaysVisible())
                refreshCode(codeRepository.findCodeBodyById(id));
            Logger logger = Logger.getLogger(WebService.class.getName());
            logger.log(Level.INFO, "getHtmlCode() : {0}", id);
            CodeBody page = codeRepository.findCodeBodyById(id);
            model.addAttribute("timeRestraint", page.getTime() > 0);
            model.addAttribute("viewsRestraint", page.isViewsLimited());
            model.addAttribute("date", page.getDate());
            model.addAttribute("code", page.getCode());
            model.addAttribute("views", page.getViews());
            model.addAttribute("time", page.getTime());
            logger.log(Level.INFO, "Date {0}", page.getDate());
            return "getSingleCode";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void refreshCode(CodeBody temp) {
        if (temp.getTime() != 0) {
            LocalDateTime timeOfCreation = LocalDateTimeConverter.reverseConvert(temp.getDate());
            temp.setTime(temp.getTime() - ChronoUnit.SECONDS.between(timeOfCreation, LocalDateTime.now()));
        }
        if (temp.isViewsLimited()) {
            temp.setViews(temp.getViews() - 1);
        }
        if (temp.getTime() < 0 | (temp.isViewsLimited() && temp.getViews() < 0)) {
            codeRepository.delete(temp);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        codeRepository.save(temp);
    }

    public String getLatest(Model model) {
        Logger logger = Logger.getLogger(WebService.class.getName());
        logger.log(Level.INFO,"getLatestCodes()");
        List<CodeBody> pages = codeRepository.findAll().stream()
                .filter(CodeBody::isAlwaysVisible)
                .sorted((x, y) -> (int) Duration.between(x.getCreationTime(), y.getCreationTime()).toMillis())
                .limit(10)
                .collect(Collectors.toList());
        model.addAttribute("pages", pages);
        return "getLatestCodes";
    }

    public ModelAndView getNew() {
        return new ModelAndView("newcode");
    }
}
