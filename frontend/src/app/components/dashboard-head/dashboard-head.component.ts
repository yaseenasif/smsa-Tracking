import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {faArrowRight} from '@fortawesome/free-solid-svg-icons'
@Component({
  selector: 'app-dashboard-head',
  templateUrl: './dashboard-head.component.html',
  styleUrls: ['./dashboard-head.component.scss']
})
export class DashboardHeadComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  logout(){
    sessionStorage.removeItem('jwtToken')
    this.router.navigateByUrl('/login')
  }

}
