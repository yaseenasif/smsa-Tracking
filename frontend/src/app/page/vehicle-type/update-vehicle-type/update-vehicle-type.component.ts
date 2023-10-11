import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-vehicle-type',
  templateUrl: './update-vehicle-type.component.html',
  styleUrls: ['./update-vehicle-type.component.scss']
})
export class UpdateVehicleTypeComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Vehicle Type',routerLink:'/vehicle-type'},{ label: 'Edit Vehicle Type'}];
  }
}
