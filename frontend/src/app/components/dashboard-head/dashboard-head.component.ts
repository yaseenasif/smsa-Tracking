import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MegaMenuItem, MenuItem, MessageService } from 'primeng/api';
@Component({
  selector: 'app-dashboard-head',
  templateUrl: './dashboard-head.component.html',
  styleUrls: ['./dashboard-head.component.scss'],
  providers:[MessageService]
})
export class DashboardHeadComponent implements OnInit {



  items: MenuItem[];
  // items: MegaMenuItem[] | undefined;

  constructor(private messageService: MessageService,private router:Router) {
      this.items = [
          {
              label: 'Logout',
              icon: 'bi bi-box-arrow-right',
              command: () => {
                this.logout();
            }
           
          },
          {
              label: 'Change Password',
              icon: 'bi bi-person-lock',
              routerLink: ['/reset-password'] 
          },
      ];
  }





  logout(){
    localStorage.removeItem('accessToken')
    this.router.navigateByUrl('/login')
  }
  
  ngOnInit() {
    
  }
  
 
  


}
