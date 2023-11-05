import { Component, OnInit } from '@angular/core';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  constructor(private authguardService:AuthguardService) { }

  configTurner:boolean=false;

  ngOnInit(): void {
  }

  turner(){
    this.configTurner=!this.configTurner;
  }
  hasPermission(permission:string):boolean{
  return this.authguardService.hasPermission(permission)
  }

}
