import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';


@Component({
  selector: 'app-add-attachments-of-international-shipment-by-road',
  templateUrl: './add-attachments-of-international-shipment-by-road.component.html',
  styleUrls: ['./add-attachments-of-international-shipment-by-road.component.scss'],
  providers:[MessageService]
})
export class AddAttachmentsOfInternationalShipmentByRoadComponent {
  items: MenuItem[] | undefined;

  constructor(
    private messageService: MessageService,
    private route:ActivatedRoute,
  ) { }
  
  size=environment.fileSize;
  url=environment.baseurl.concat('/add-international-attachments/',this.route.snapshot.paramMap.get('id')!)
  uploadedFiles!:any[];

  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
  });

  onUpload(event:FileUploadEvent,name:string){
   this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: name.concat(' file is uploaded')});
  }
 
  onError(event:FileUploadErrorEvent){
    this.messageService.add({severity: 'error', summary: 'File Uploaded', detail: event.error?.error.body});
  }

  ngOnInit(): void {  
    this.items = [{ label: 'International Outbound',routerLink:'/international-tile'},{ label: 'International Outbound By Road',routerLink:'/international-shipment-by-road'},{ label: 'Add Attachments'}];
  }
}
