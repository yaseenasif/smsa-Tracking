import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { VehicleTypeService } from '../service/vehicle-type.service';
import { VehicleType } from 'src/app/model/VehicleType';

@Component({
  selector: 'app-vehicle-type-list',
  templateUrl: './vehicle-type-list.component.html',
  styleUrls: ['./vehicle-type-list.component.scss'],
  providers:[MessageService]
})
export class VehicleTypeListComponent implements OnInit {
  vTID!: number;
  visible: boolean=false;
  constructor(private vehicleTypeService:VehicleTypeService,private messageService:MessageService) { }
  vehicleTypes!:VehicleType[];
  items: MenuItem[] | undefined;

  ngOnInit() {
      this.items = [{ label: 'Vehicle Type'}];
      this.getAllVehicleType();
  }

  getAllVehicleType(){
    this.vehicleTypeService.getALLVehicleType().subscribe((res:VehicleType[])=>{
      this.vehicleTypes=res;  
    },error=>{
    })
   }

   showDialog(id:number) {
    this.vTID=id;
    this.visible = true;
   }

   deleteVehicleTypeByID(id:number){
    this.vehicleTypeService.deleteVehicleTypeByID(id).subscribe((res:VehicleType)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Permission is deleted on id '+res!.id!.toString()});
      this.getAllVehicleType();
    },error=>{
      
    });
   }

}
