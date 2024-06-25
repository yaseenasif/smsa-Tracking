import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { User } from 'src/app/model/User';
import { UserService } from '../service/user.service';
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers:[MessageService]
})
export class UserListComponent implements OnInit {

  items: MenuItem[] | undefined;
  users!:User[]
  page: number=0;
  size: number=10;
  totalRecords!: number;
  apiResponse!: any;

  visible: boolean = false;
  uID!:number
  searchCriteria={
     location:"",
     email:"",
     name:"",
     role:"",
     status:true
   }
   

  constructor(private userService:UserService,private messageService:MessageService,private authguardService:AuthguardService) { }
  
  ngOnInit() {
      this.items = [{ label: 'User List'}];
      this.getAllUser(this.size ,this.page,this.searchCriteria);
  }

  searchByFilter() {
    this.getAllUser(this.size,0,this.searchCriteria);
  }
  clearFilter() {
  this.searchCriteria={
    location:"",
    email:"",
    name:"",
    role:"",
    status:true
  }
  this.getAllUser(this.size,0,this.searchCriteria);
  }
  onPageChange(event: any) {
    this.page = event.page;
    this.size = event.rows;
    this.getAllUser(this.size ,this.page,this.searchCriteria);
  }



  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

  getAllUser(size:number,page:number,searchCriteria:any){
    this.userService.getAllUser(page,size,searchCriteria).subscribe((res:PaginatedResponse<User>)=>{
      
      this.users=res.content;  
      this.page=res.pageable.pageNumber;
      this.size=res.size;
      this.totalRecords = res.totalElements;
      this.apiResponse =res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
   }

   deleteUserByID(id:number){
    this.userService.deleteUserByID(id).subscribe((res:User)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'User is deleted on id '+res!.id!.toString()});
      this.getAllUser(this.size ,this.page,this.searchCriteria);
    },error=>{
      this.visible = false;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

  showDialog(id:number) {
    this.uID=id;
    this.visible = true;
  }
  
}
