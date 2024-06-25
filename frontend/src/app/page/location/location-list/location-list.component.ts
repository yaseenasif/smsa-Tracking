import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationService } from '../service/location.service';
import { Location } from '../../../model/Location'
import { AuthguardService } from 'src/app/auth-service/authguard/authguard.service';
import { PaginatedResponse } from 'src/app/model/PaginatedResponse';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.scss'],
  providers:[MessageService]
})
export class LocationListComponent implements OnInit {
  page: number=0;
  size: number=10;
  totalRecords!: number;
  apiResponse!: any;


  constructor(private locationService:LocationService,private messageService:MessageService,private authguardService:AuthguardService) { }
 
  items: MenuItem[] | undefined;
  location!:Location[];
  visible: boolean = false;
  lID!:number

  searchCriteriaForLocation={
    locationName:"",
    locationType:"",
    facility:"",
    country:"",
    status:true
 }

  ngOnInit() {
      this.items = [{ label: 'Location List'}];
      this.getAllLocations(this.size,this.page,this.searchCriteriaForLocation);
  }

  searchByFilter() {
    this.getAllLocations(this.size,0,this.searchCriteriaForLocation);
  }
  clearFilter() {
  this.searchCriteriaForLocation={
    locationName:"",
    locationType:"",
    facility:"",
    country:"",
    status:true
  }
  this.getAllLocations(this.size,0,this.searchCriteriaForLocation);
  }
  onPageChange(event: any) {

    this.page = event.page;
    this.size = event.rows;
    this.getAllLocations(this.size ,this.page,this.searchCriteriaForLocation);
  }
  

  
  
  getAllLocations(size:number,page:number,value:any){
    this.locationService.getAllFilterLocation(size,page,value).subscribe((res:PaginatedResponse<Location>)=>{
      this.location=res.content.filter(el => el.status);   
      this.page=res.pageable.pageNumber;
      this.size=res.size;
      this.totalRecords = res.totalElements;
      this.apiResponse =res;
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteLocationByID(id:number){
    this.locationService.deleteLocationByID(id).subscribe((res:Location)=>{
      this.getAllLocations(10,0,this.searchCriteriaForLocation);
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is deleted on id '+res!.id!.toString()});
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
   }

  showDialog(id:number) {
    this.lID=id;
    this.visible = true;
  }

  hasPermission(permission:string):boolean{
    return this.authguardService.hasPermission(permission)
  }

}
