import { Component, OnInit } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { LocationPortService } from '../service/location-port.service';
import { LocationPort } from 'src/app/model/LocationPort';

@Component({
  selector: 'app-location-port-list',
  templateUrl: './location-port-list.component.html',
  styleUrls: ['./location-port-list.component.scss'],
  providers:[MessageService]
})
export class LocationPortListComponent implements OnInit {
  

  constructor(private locationPortService:LocationPortService,private messageService:MessageService) { }
 
  lpID!: number;
  items: MenuItem[] | undefined;
  locationPort!:LocationPort[]
  visible: boolean = false;

  ngOnInit() {
      this.items = [{ label: 'Location Port List'}];
      this.getAllLocationPort();
  }

  getAllLocationPort(){
    this.locationPortService.getAllLocationPort().subscribe((res:LocationPort[])=>{
      this.locationPort=res.filter(el=>el.status)
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteLocationPortByID(id:number){
    this.locationPortService.deleteLocationPortByID(id).subscribe((res:LocationPort)=>{
      this.visible = false;
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location Port is deleted on id '+res!.id!.toString()});
      this.getAllLocationPort();
    },error=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
  }

  showDialog(id:number) {
    this.lpID=id;
    this.visible = true;
  }

}
