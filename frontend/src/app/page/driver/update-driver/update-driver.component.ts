import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-update-driver',
  templateUrl: './update-driver.component.html',
  styleUrls: ['./update-driver.component.scss']
})
export class UpdateDriverComponent implements OnInit {

  items: MenuItem[] | undefined;

  constructor() { }
  name!:string;
  contactNumber!:string;
  referenceNumber!:string;
  ngOnInit(): void {
    this.items = [{ label: 'Driver List',routerLink:'/driver'},{ label: 'Edit Driver'}];
  }

}
