# Formation Lens

## Pré-requis

* Minikube running
* avoir accès au VPN OVH

## Rappels Théoriques

### Conteneurisation

| Items | Virtualisation | Conteneurisation |
|---|---|---|
| Element de base | Machine virtuelle | conteneur |
| OS | Propre | Partagé avec l'hôte |
| Outil de gestion | Hyperviseur | Orchestrateur |
| Exemples de technologies | VMWare, Workstation | Docker, Podman |
|  |  |  |

	

### Kubernetes

#### Elements de base

##### Node

Machine physique ou virtuelle qui héberge tout ou partie du cluster.

##### Pods

Ensemble de conteneurs partageant des ressources (file system, réseau, ...). 
Un pod a notamment :
* un nom unique généré dynamiquement (ex : mon-super-pod-qsdfjsh-qffqsdf)
* des labels
* une liste de conteneurs
* une liste de sondes (startup, readyness, liveness) avec leurs url, périodes de grace, seuil d'échec et seuil de succès
* une liste de volumes

Un pod peut être ciblé par une requête directement par son nom ou sélectionné par ses labels pour recevoir le trafic d'un service.

##### Services

Abstraction et Load Balancer pour les pods sélectionnés pour servir ce service.

Un service peut être de plusieurs natures, notamment : ClusterIP et NodePort.

###### ClusterIP

Un service ClusterIP interne est accessible par sont nom uniquement depuis l'intérieur du cluster.

###### NodePort

Un service NodePort expose le service sur l'IP de chaque nœud sur un port statique.

###### IP externes

Permet d'associer une IP externe à un service. Appeler le cluster avec cette IP redirige directement vers ce service.

##### Deployments

Template d'ensemble (pods + services).

##### Persistent Volume Claim

Un Persistent Volume Claim permet d'associer un Persistent Volume à un Deployment.

##### Persistent Volume 

Un Persistent Volume est un emplacement pour stocker des données de manière pérenne (comme les volumes dans Docker).  
Les PV sont en dehors des namespaces.  
On ne peut associer un PV a plusieurs pods qu'en Read Only.

##### StatefulSet

Un statefulset est un deployment spécifique utilisé principalement pour les persistances.  
Il permet d'avoir des pods avec des noms et des associations PVC -> PV déterministes.  
Donc lorsqu'on détruit le pod 'database-0', un pod 'database-0' est recréé et le même PV lui sera associé.
Ainsi dans le cas d'une base de données avec plusieurs pods, le pod retrouve 'ses' données.

##### Ingress

Point d'entrée dans le cluster qui permet de rediriger le trafic entrant vers des services.

Une ingress est constituée de :  
* un hôte
* un port
* une cible

Dans l'exemple suivant, la requete 'http://thess-app.fr/' sera redirigée vers le service 'web-v1-thess' sur le port 80 sans réécriture et la requete 'http://www.sy-nephro.fr/' sera redirigée vers le service 'web-v1-nephro' sur le port 80 sans réécriture.

```
apiVersion: networking.k8s.io/v1
kind: Ingress
spec:
  ingressClassName: haproxy
  rules:
    - host: thess-app.fr
      http:
        paths:
          - backend:
              service:
                name: web-v1-thess
                port:
                  number: 80
            path: /
            pathType: Prefix
    - host: www.sy-nephro.fr
      http:
        paths:
          - backend:
              service:
                name: web-v1-nephro
                port:
                  number: 80
            path: /
            pathType: Prefix
```

#### Cinématique

Une requête arrive sur un node du cluster.  
Le cluster vérifie ses ingress pour voir celle qui correspond.  
Le trafic est redirigé vers le service pointé par l'ingress.  
Le trafic est redirigé vers le pod sélectionné par le service.  

## Installation 

Hors connexion VPN pour aller plus vite:

* Télécharger Lens desktop https://k8slens.dev/
* Créer un compte LensID
* être connecté sur leur site et saisir l'url https://app.k8slens.dev/subscribe/personal/login

## Mise en place

### Ajout de connexions

#### Depuis un fichier local

* Se connecter au cluster Minikube en important le fichier kubeconfig
	* Dans le menu, aller dans l'onglet Catalog
	* Ajouter un cluster depuis un fichier local avec le '+' en bas à droite
		* Sélectionner le fichier ~/.kube/config

#### Depuis Rancher

* Se connecter au cluster Préprod-1 en important le fichier kubeconfig (connexion OVN obligatoire)
	* Dans Rancher https://rancher.lavaleriane.fr
		* Dans le menu burger, aller dans le cluster Preprod-1 
		* Copier le fichier kubeconfig dans le clipboard avec le bouton qui va bien en haut à droite
	* Dans Minikube, aller dans l'onglet Catalog
	* Ajouter un cluster depuis un fichier copié avec le '+' en bas à droite
		* Coller le fichier copié dans Rancher

## Visite guidée

| Onglets | Role | Type d'utilisation |
|---|---|---|
| Cluster | Page d'accueil | Diagnostique rapide : Y a t il un souci ?|
| Applications | Récap des applications Helm installées | Listing : Qu'est ce que j'ai d'installé ? |
| Nodes | Etat des nodes du clusters | Diagnostique rapide : Est ce que mes machines vont bien ?|
| Workloads | Accès et actions sur les éléments principaux du cluster | Utilisation principale : Qu'est ce qui ne marche pas ? |
| Config | Accès et actions sur les config au sens large | Diagnostique des configurations : Pourquoi mon appli n'arrive pas à accéder à une ressource (API, Database, ...) ? |
| Network | Accès et actions sur les éléments réseaux (ingress, services, ...) | Diagnostique réseaux : Pourquoi ma requête n'atteint pas mon serveur ? |
| Storage |  | Pourquoi je ne retrouve pas mes données ? |
| Namespaces | Listing des namespaces | visite : Comment est organisé mon cluster ? |
| Events | Listing des événements | Diagnostique avancé : Que se passe-t-il sur mon cluster ? |
| Helm | Market place | Shopping : Qu'est ce que je vais installer aujourd'hui ? |
| Access Control | Sécurité | Utilisation Ultra-avancée++ : TPPC ! (Touche Pas P'tit Con) |
| Custom Resources | Let me sing the song of my people | Ce ne sont pas les onglets que vous cherchez ! |

## Un peu de pratique

==Afin de limiter les potentiels bêtises sur la préprod, tous les exercices suivant se feront sur le cluster minikube.==


### Navigation dans le cluster Minikube

#### N'afficher que les ressources du namespace local

* Sélectionner le(s) namespace(s) désiré(s) en haut à droite

### Analyse et Debug

#### Consulter les événements du cluster

* Dans Events, consultez les événements par namespace
* Modifiez un deployment pour mettre un nom ou un tag d'image inexistant
* Consultez les événements pour voir ce qu'il se passe

#### Aller lire les logs d'un pod 

* Dans l'onglet Workloads > Pods
	* Sélectionner un pod (ex : thess-alert-xxx)
	* Dans le menu en haut de l'onglet de droite, cliquer sur le bouton de visualisation de logs

### Intervention sur les pods

#### Supprimer un pod

* Dans l'onglet Workloads > Pods
	* Sélectionner un pod (ex : web-v1-thess-xxx)
	* Dans le menu en haut de l'onglet de droite, supprimer le pod
	* Observer l'effet dans l'onglet Pods

#### Exécuter des commandes dans un pod

* Dans l'onglet Workloads > Pods
	* Sélectionner un pod (ex : web-v1-thess-xxx)
	* Dans le menu en haut de l'onglet de droite, cliquer sur le bouton 'pod shell' pour ouvrir un terminal
		* Exécuter quelques commandes. Ex:
			* cd /usr/share/nginx/html/
			* ls

#### Changer la replication d'un pod

* Dans l'onglet Workloads > Deployment
	* Sélectionner un deployment
	* Changer sa replication (scale)
	* Observer l'effet dans l'onglet Pods

### Collecter des informations de configuration

#### Lire les variables d'environnement d'un pod

* Dans l'onglet Workloads > Pods
	* Sélectionner un pod (ex : thess-alert-xxx)
	* Dans le menu en haut de l'onglet de droite, descendre jusqu'au cadre 'Environment' pour lire toutes les variables d'environnement positionnées dans le pod

#### Lire et Modifier les ConfigMap associées à un deployment

* Dans l'onglet Workloads > Deployment, choisissez un deployment (ex : thess-alert-xxx) et éditez le.
* Dans le fichier yaml, trouvez les valeurs de l'élément spec.template.spec.containers.envFrom et choisissez le nom d'une configMap (ex : thess-alert-api-cm)
* Dans Config > Config Maps, sélectionnez la configMap voulue et éditez la
	* Dans le fichier yaml, changez une valeur (ex: LOGGING_LEVEL_ROOT : INFO en WARN)
	* Enregistrez les changements
	* Consultez la valeur de la variable d'environnement dans un pod utilisant cette ConfigMap (inchangée)
	* Supprimez le pod
	* Consultez la valeur de la variable d'environnement dans un pod utilisant cette ConfigMap (mise à jour)

### Créer une ressource

* Cliquez sur le '+' au niveau des terminaux (ou tout en bas de l'écran vers la gauche s'il n'y a pas de terminal d'ouvert)
* Sélectionnez 'Create resource'
* Sélectionnez un template (ex : ConfigMap) et éditez la comme vous le souhaitez puis sauvegardez
* Allez dans l'onglet de votre ressource et constatez la création de l'entité


#### Créer un job pi

https://kubernetes.io/docs/concepts/workloads/controllers/job/


