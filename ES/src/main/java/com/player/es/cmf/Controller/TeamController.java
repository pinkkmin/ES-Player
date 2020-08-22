package com.player.es.cmf.Controller;

import com.player.es.cmf.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TeamController {
    @Autowired
    TeamService teamService;

}
