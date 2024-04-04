import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DriverService } from '../service/driver.service';
import { Driver } from 'src/app/model/Driver';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.scss'],
  providers:[MessageService]
})
export class DriverListComponent implements OnInit {
  visible!: boolean;
  dId!:number;
  items: MenuItem[] | undefined;
  drivers!:Driver[]

  constructor(private driverService:DriverService,private messageService:MessageService,private authguardService:AuthguardService) { }
  
 

  ngOnInit() {
      this.items = [{ label: 'Driver List'}];
      this.getAllDriver();
  }
  
  getAllDriver(){
   this.driverService.getAllDriver().subscribe((res:PaginatedResponse<Driver>)=>{
    
    this.drivers=res.content.filter((el:Driver)=>el.status); 

    
   },error=>{
    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
   })
  }

  deleteDriverByID(id:number){
    this.driverService.deleteDriverByID(id).subscribe((res:Driver)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Driver is deleted on id '+res!.id!.toString()});
      this.getAllDriver();
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

  showDialog(id:number) {
    this.dId=id;
    this.visible = true;
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

}
