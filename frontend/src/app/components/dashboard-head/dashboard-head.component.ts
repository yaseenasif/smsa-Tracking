import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
    localStorage.removeItem('accessToken')
    this.router.navigateByUrl('/login')
  }

}
