import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { DriverService } from '../service/driver.service';
import { Driver } from 'src/app/model/Driver';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';

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

  constructor(private driverService:DriverService,private messageService:MessageService) { }
  
 

  ngOnInit() {
      this.items = [{ label: 'Driver List'}];
      this.getAllDriver();
  }
  
  getAllDriver(){
   this.driverService.getAllDriver().subscribe((res:PaginatedResponse<Driver>)=>{
    
    this.drivers=res.content.filter((el:Driver)=>el.status); 

    
   },error=>{})
  }

  deleteDriverByID(id:number){
    this.driverService.deleteDriverByID(id).subscribe((res:Driver)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Driver is deleted on id '+res!.id!.toString()});
      this.getAllDriver();
    },error=>{
      
    });
   }

  showDialog(id:number) {
    this.dId=id;
    this.visible = true;
  }

}
