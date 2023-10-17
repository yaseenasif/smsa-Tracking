import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  items: MenuItem[] | undefined;
 
  

  constructor() { }

  name!:string;
  email!:string;
  password!:string;
  role!:string;
  location!:Location[];
  selectedLocation!:Location;

  ngOnInit(): void {
    this.items = [{ label: 'User',routerLink:'/user'},{ label: 'Add User'}];

    this.location=[
      {
        locationName:"karachi",
        id:1
      },
      {
        locationName:"kaAAi",
        id:2
      },
      {
        locationName:"Alld",
        id:3
      },
      {
        locationName:"islamabad",
        id:4
      },
      {
        locationName:"lahore",
        id:5
      },
    ]
  }

}

interface Location{
  locationName:string,
  id:number
}
