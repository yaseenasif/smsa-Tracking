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
  configTurner2:boolean=false;
  configTurner3:boolean=false;
   

  ngOnInit(): void {
  }

  turner(){
    this.configTurner=!this.configTurner;
  }
  turner2(){
    this.configTurner2=!this.configTurner2;
  }
  turner3(){
    this.configTurner3=!this.configTurner3;
  }
  hasPermission(permission:string):boolean{
  return this.authguardService.hasPermission(permission)
  }

}
