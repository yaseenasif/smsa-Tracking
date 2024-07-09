import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { VehicleTypeService } from '../service/vehicle-type.service';
import { Vehicle } from 'src/app/model/VehicleType';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';

@Component({
  selector: 'app-vehicle-type-list',
  templateUrl: './vehicle-type-list.component.html',
  styleUrls: ['./vehicle-type-list.component.scss'],
  providers:[MessageService]
})
export class VehicleTypeListComponent implements OnInit {
  vTID!: number;
  visible: boolean=false;
  constructor(private vehicleTypeService:VehicleTypeService,private messageService:MessageService,private authguardService:AuthguardService) { }
  vehicles!:Vehicle[];
  items: MenuItem[] | undefined;

  ngOnInit() {
      this.items = [{ label: 'Vehicle'}];
      this.getAllVehicleType();
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllVehicleType(){
    this.vehicleTypeService.getALLVehicleType().subscribe((res:Vehicle[])=>{
      this.vehicles=res.filter((res)=>res.status);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   showDialog(id:number) {
    this.vTID=id;
    this.visible = true;
   }

   deleteVehicleTypeByID(id:number){
    this.vehicleTypeService.deleteVehicleTypeByID(id).subscribe((res:Vehicle)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vehicle type is deleted on id '+res!.id!.toString()});
      this.getAllVehicleType();
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

}
