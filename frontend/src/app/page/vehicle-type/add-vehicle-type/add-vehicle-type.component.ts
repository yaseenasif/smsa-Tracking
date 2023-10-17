import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-vehicle-type',
  templateUrl: './add-vehicle-type.component.html',
  styleUrls: ['./add-vehicle-type.component.scss']
})
export class AddVehicleTypeComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Vehicle Type',routerLink:'/vehicle-type'},{ label: 'Add Vehicle Type'}];
  }
}
