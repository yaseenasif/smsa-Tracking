import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MegaMenuItem, MenuItem, MessageService } from 'primeng/api';
import { Menu } from 'primeng/menu';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
@Component({
  selector: 'app-dashboard-head',
  templateUrl: './dashboard-head.component.html',
  styleUrls: ['./dashboard-head.component.scss'],
  providers:[MessageService]
})
export class DashboardHeadComponent implements OnInit {



  items: MenuItem[];
  @ViewChild('menu') menu!: Menu;
  isMenuOpen: boolean = false;
  user:any={employeeId:null,roles:null}
  // items: MegaMenuItem[] | undefined;

  constructor(private messageService: MessageService,private router:Router,private authguardService:AuthguardService) {
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
    // debugger
  const token=this.authguardService.getDecodedAccessToken(localStorage.getItem('accessToken')!);
    this.user.employeeId=token["sub"]
    this.user.roles=token["ROLES"];
  }
  
  toggleMenu(event: Event) {
    event.stopPropagation();
    if (this.menu) {
      this.menu.toggle(event);
      this.isMenuOpen = this.menu.overlayVisible!;
    }
  }
  


}
