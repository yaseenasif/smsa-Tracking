import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MenuItem, MessageService} from 'primeng/api'
import { RoleService } from '../../role/service/role.service';
import { LocationService } from '../../location/service/location.service';
import { UserService } from '../service/user.service';
import { User } from 'src/app/model/User';
import { Role } from 'src/app/model/Role';
import {Location} from '../../../model/Location'

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss'],
  providers:[MessageService]
})
export class UpdateUserComponent implements OnInit {
  items: MenuItem[] | undefined;
 
  

  constructor (private route: ActivatedRoute,private router: Router,private messageService:MessageService,private roleService:RoleService,private locationService:LocationService,private userService:UserService) { }

  name!:string;
  email!:string;
  password!:string;
  role!: string
  selectedLocation!:Location;

  user:User={
    email: null,
    id: null,
    location: null,
    name: null,
    password: null,
    role: null
  };
  roles!:Role[];
  locations!:Location[];
  userid?:number;

  ngOnInit(): void {
    this.userid = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'User',routerLink:'/user'},{ label: 'Edit User'}];
    this.getAllLocations();
    this.getAllLRole();
    this.getUserById(this.userid);
  }

  getAllLocations(){
    this.locationService.getAllLocation().subscribe((res:Location[])=>{
      this.locations=res.filter(el => el.status);   
    },error=>{
    })
  }
  
  getAllLRole(){
    this.roleService.getALLRole().subscribe((res:Role[])=>{
      this.roles=res;
        
    },error=>{  
    })
  }

  getUserById(id:number){
    this.userService.getUserByID(id).subscribe((res:any)=>{
      this.user=res;
    },(error:any)=>{
      debugger
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User is not deleted' });

    })
  }

  onSubmit(){
    this.userService.updateUserById(this.userid!,this.user).subscribe(res=>{
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is updated' });
      setTimeout(() => {
        this.router.navigate(['/user']);
      },800);
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'User is not updated' });
    })  
  }

}


