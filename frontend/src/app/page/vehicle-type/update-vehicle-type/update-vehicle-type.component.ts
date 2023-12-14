import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { VehicleTypeService } from '../service/vehicle-type.service';
import { ActivatedRoute, Router } from '@angular/router';
import { VehicleType } from 'src/app/model/VehicleType';

@Component({
  selector: 'app-update-vehicle-type',
  templateUrl: './update-vehicle-type.component.html',
  styleUrls: ['./update-vehicle-type.component.scss'],
  providers:[MessageService]
})
export class UpdateVehicleTypeComponent implements OnInit {

  items: MenuItem[] | undefined;
  vTID!: number;
  vehicleType:VehicleType={
    id: null,
    name: null
  }

  constructor(private route: ActivatedRoute,
    private vehicleTypeService:VehicleTypeService,
    private messageService: MessageService,
    private router: Router) { }

  name!:string;
  
  ngOnInit(): void {
    this.vTID = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Vehicle Type',routerLink:'/vehicle-type'},{ label: 'Edit Vehicle Type'}];
    this.getVehicleTypeById();
  }

  getVehicleTypeById(){
    this.vehicleTypeService.getByIDVehicleType(this.vTID).subscribe((res:VehicleType)=>{
     this.vehicleType.name=res.name;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   onSubmit() {
    this.vehicleTypeService.updateVehicleTypeById(this.vTID,this.vehicleType).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Vehicle Type is updated on id'+res.id});
      setTimeout(() => {
        this.router.navigate(['/vehicle-type']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
}
