import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { FileUploadErrorEvent, FileUploadEvent } from 'primeng/fileupload';
import { InternationalShippingService } from '../../service/international-shipping.service';


@Component({
  selector: 'app-add-attachments-of-international-shipment-by-road',
  templateUrl: './add-attachments-of-international-shipment-by-road.component.html',
  styleUrls: ['./add-attachments-of-international-shipment-by-road.component.scss'],
  providers:[MessageService]
})
export class AddAttachmentsOfInternationalShipmentByRoadComponent {
  items: MenuItem[] | undefined;

  aWBCopies:any[]=[]
  invoices:any[]=[]
  manifest:any[]=[]
  transitManifest:any[]=[]
  vehicleManifest:any[]=[]
  exportBayan:any[]=[]
  otherAttachments:any[]=[]

  constructor(
    private messageService: MessageService,
    private route:ActivatedRoute,
    private internationalShippingService:InternationalShippingService
  ) { }
  
  size=environment.fileSize;
  url=environment.baseurl.concat('/add-international-attachments/',this.route.snapshot.paramMap.get('id')!)
  uploadedFiles!:any[];

  headers = new HttpHeaders({
    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
  });

  onUpload(event:FileUploadEvent,name:string){
   this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: name.concat(' file is uploaded')});
   this.fileMetaDataOfIS();
  }
 
  onError(event:FileUploadErrorEvent){
    this.messageService.add({severity: 'error', summary: 'File Uploaded', detail: event.error?.error.body});
  }

  fileMetaDataOfIS(){
    this.aWBCopies=[]
    this.invoices=[]
    this.manifest=[]
    this.transitManifest=[]
    this.vehicleManifest=[]
    this.exportBayan=[]
    this.otherAttachments=[]
  
    this.internationalShippingService.getFileMetaDataByInternationalShipment(Number( this.route.snapshot.paramMap.get('id')!)).subscribe((res)=>{
        res.forEach((element:any) => {
          if(element.attachmentType == "AWB Copies"){
            this.aWBCopies.push(element)
          }else if(element.attachmentType == "Invoices"){
            this.invoices.push(element)
          }else if(element.attachmentType == "Manifest"){
            this.manifest.push(element)
          }else if(element.attachmentType == "Transit Manifest"){
            this.transitManifest.push(element)
          }else if(element.attachmentType == "Vehicle Manifest"){
            this.vehicleManifest.push(element)
          }else if(element.attachmentType == "Export Bayan"){
            this.exportBayan.push(element)
          }else if(element.attachmentType == "Other Attachments"){
            this.otherAttachments.push(element)
          }
        });
     },(error)=>{
      this.messageService.add({severity: 'error', summary: error.error.body});
     })
  }
  deleteAttachment(id:number){
    this.internationalShippingService.deleteAttachment(id).subscribe((res)=>{
      this.fileMetaDataOfIS();
      this.messageService.add({severity: 'success', summary: 'Success', detail:"File has been deleted successfully."});
      
    },(error)=>{
      this.messageService.add({severity: 'error', summary: 'Error', detail:"File could not be deleted due to some problem."});
    });
  }

  ngOnInit(): void {  
    this.items = [{ label: 'International Outbound',routerLink:'/international-tile'},{ label: 'International Outbound By Road',routerLink:'/international-shipment-by-road'},{ label: 'Add Attachments'}];
    this.fileMetaDataOfIS();
  }
}
