import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MenuItem, MessageService} from 'primeng/api'
import { Role } from 'src/app/model/Role';
import { User } from 'src/app/model/User';
import { UserService } from '../service/user.service';
import { LocationService } from '../../location/service/location.service';
import { RoleService } from '../../role/service/role.service';
import {Location} from '../../../model/Location'

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss'],
  providers:[MessageService]
})
export class UpdateUserComponent implements OnInit {
  items: MenuItem[] | undefined;

  constructor(private router: Router,private route:ActivatedRoute,private messageService:MessageService,private roleService:RoleService,private locationService:LocationService,private userService:UserService) { }

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
    status: undefined,
    employeeId: undefined
  };
  selectedRole!:Role;
  roles:Role[]=[];
  locations!:Location[];


  userId?:number;
  name!:string;
  email!:string;
  password!:string;
  role!:string;
  location!:Location[];
  selectedLocation!:Location;

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id')!;
    this.getAllLocations();
    this.getAllLRole();
    this.getUserById(this.userId);
    this.items = [{ label: 'User',routerLink:'/user'},{ label: 'Edit User'}];

  }

  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.locations=res.filter(el => el.status);   
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  getAllLRole(){
    this.roleService.getALLRole().subscribe((res:Role[])=>{
      // res[0].permissions= res[0].permissions!.sort((a,b)=> a.id!-b.id!)
      res.map((el)=>{
        return el.permissions?.sort((a,b)=>a.id!-b.id!)
      })
      this.roles=res;
    },error=>{ 
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body }); 
    })
  }

  getUserById(userId:number){
    this.userService.getUserById(userId).subscribe((res:User)=>{
      res.roles![0].permissions=res.roles![0].permissions!.sort((a,b)=> a.id!-b.id!)
      this.user=res;
      console.log(this.user);
      
    },(error:any)=>{
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onSubmit(){
  
    this.userService.updateUserById(this.userId!,this.user).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is updated successfully' });
      setTimeout(() => {
        this.router.navigate(['/user']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })  
  }

}

