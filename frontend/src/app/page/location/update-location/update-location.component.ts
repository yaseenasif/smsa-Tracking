import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-location',
  templateUrl: './update-location.component.html',
  styleUrls: ['./update-location.component.scss']
})
export class UpdateLocationComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  
  ngOnInit(): void {
    this.items = [{ label: 'Location List',routerLink:'/location'},{ label: 'Edit Location'}];
  }


}
