import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { VehicleTypeService } from '../service/vehicle-type.service';
import { VehicleType } from 'src/app/model/VehicleType';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-vehicle-type',
  templateUrl: './add-vehicle-type.component.html',
  styleUrls: ['./add-vehicle-type.component.scss'],
  providers:[MessageService]
})
export class AddVehicleTypeComponent implements OnInit {

  items: MenuItem[] | undefined;
  vehicleType:VehicleType={
    id: null,
    name: null,
    occupancy:null
  };

  constructor(private vehicleTypeService:VehicleTypeService,
              private messageService: MessageService,
              private router: Router) { }
 
  
  ngOnInit(): void {
    this.items = [{ label: 'Vehicle Type',routerLink:'/vehicle-type'},{ label: 'Add Vehicle Type'}];
  }

  onSubmit() {
    
    this.vehicleTypeService.addVehicleType(this.vehicleType).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vehicle Type is added' });
      setTimeout(() => {
        this.router.navigate(['/vehicle-type']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }

}
