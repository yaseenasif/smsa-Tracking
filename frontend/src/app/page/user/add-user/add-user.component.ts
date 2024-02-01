import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { User } from 'src/app/model/User';
import { LocationService } from '../../location/service/location.service';
import { UserService } from '../service/user.service';
import {Location} from '../../../model/Location'
import { RoleService } from '../../role/service/role.service';
import { Role } from 'src/app/model/Role';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
  providers:[MessageService]
})
export class AddUserComponent implements OnInit {

  items: MenuItem[] | undefined;
  checked: boolean = false;
  

  constructor (private router: Router,private messageService:MessageService,private roleService:RoleService,private locationService:LocationService,private userService:UserService) { }


  user:User={
    email: null,
    id: null,
    name: null,
    password: null,
    roles: [],
    locations: [],
    domesticOriginLocations: [],
    domesticDestinationLocations: [],
    internationalAirOriginLocation: [],
    internationalAirDestinationLocation: [],
    internationalRoadOriginLocation: [],
    internationalRoadDestinationLocation: [],
    status: undefined
  };
  roles!:Role[];
  locationsList!:Location[];
  // Locations!:Location[];
  // OrgLocations!:Location[];
  // DesLocations!:Location[];

  ngOnInit(): void {
    this.items = [{ label: 'User',routerLink:'/user'},{ label: 'Add User'}];
    this.getAllLocations();
    this.getAllLRole();
  }

  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.locationsList=res.filter(el => el.status);   
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }
  
  getAllLRole(){
    this.roleService.getALLRole().subscribe((res:Role[])=>{
      this.roles=res;
      debugger
    },error=>{ 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body }); 
    })
  }

  onSubmit(){ 
    this.userService.addUser(this.user).subscribe(res=>{
      debugger
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is added' });
      setTimeout(() => {
        this.router.navigate(['/user']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }
   


}


